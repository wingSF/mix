package com.wing.lynne.niosync;

import org.joda.time.DateTime;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {

        try {

            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Server start up at " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {

        while (!stop) {

            try {

                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();

                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();

                    handleInput(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {

        if (key.isValid()) {

            if (key.isAcceptable()) {

                ServerSocketChannel serverScoketChannel = (ServerSocketChannel) key.channel();

                SocketChannel socketChannel = serverScoketChannel.accept();

                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }

            if (key.isReadable()) {

                SocketChannel socketChannel = (SocketChannel) key.channel();

                ByteBuffer readBuffer = ByteBuffer.allocate(1024);

                int readBytes = socketChannel.read(readBuffer);

                if (readBytes > 0) {

                    readBuffer.flip();

                    byte[] bytes = new byte[readBuffer.remaining()];

                    readBuffer.get(bytes);

                    String body = new String(bytes, "UTF-8");
                    System.out.println("body = " + body);

                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? DateTime.now().toString("yyyy-MM-dd HH:mm:ss") : "BAD ORDER";

                    doWrite(socketChannel, currentTime);

                } else if (readBytes < 0) {
                    key.cancel();
                    socketChannel.close();
                } else {
                    System.out.println("read 0 byte do nothing");
                }

            }

        }

    }

    private void doWrite(SocketChannel socketChannel, String currentTime) throws IOException {

        if (currentTime != null && currentTime.trim().length() != 0) {
            byte[] bytes = currentTime.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }


    }
}
