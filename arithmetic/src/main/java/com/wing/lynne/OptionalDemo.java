package com.wing.lynne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.html.Option;
import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {

        C cc = new C();
        cc.setName("hah");

        B bb = new B();

        A aa = new A();
        A aaa = new A();
        aa.setB(bb);
        bb.setC(cc);


        String s = Optional.ofNullable(aa)
                .map(a -> a.getB())
                .map(b -> b.getC())
                .map(c -> c.getName())
                .orElse(null);

        String s1 = Optional.of("String").orElse(getName());
        String s2 = Optional.of("String").orElseGet(() -> getName());
        Object o1 = Optional.ofNullable(null).orElse(getName());
        Object o2 = Optional.ofNullable(null).orElseGet(() -> getName());
        Object o3 = Optional.ofNullable(null).orElseThrow(IllegalStateException::new);
    }

    public Optional<C> getUser(String name) {
        if (StringUtils.isNotEmpty(name)) {
            return Optional.ofNullable(new C(name));
        } else {
            return Optional.empty();
        }
    }

    public static String getName() {
        System.out.println("method call");
        return "method getName";
    }
}


@Data
class A {
    private B b;
}

@Data
class B {
    private C c;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class C {
    private String name;
}
