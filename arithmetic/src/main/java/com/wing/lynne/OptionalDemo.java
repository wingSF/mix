package com.wing.lynne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OptionalDemo {

    public static void main(String[] args) {

        D dd = new D();
        HashMap<Object, Object> demoMap = new HashMap<>();
        demoMap.put("haha","wtm");
        dd.setDemoMap(demoMap);

        boolean ifEmpty = Optional.ofNullable(dd)
                .map(d -> d.getDemoMap())
                .orElse(new HashMap())
                .isEmpty();

        System.out.println(ifEmpty);

        C cc = new C();
        cc.setName("hah");

        B bb = new B();

        A aa = null;
        A aaa = new A();
//        aa.setB(bb);
//        bb.setC(cc);


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

@Data
@NoArgsConstructor
@AllArgsConstructor
class D{
    private Map demoMap;
}