package com.wing.lynne.example1;

import java.lang.annotation.Documented;

@Documented//加入该注解可以保证类上面的注释 加入到javadoc里面
@interface ClassPreamble {

    String author();
    String date();
    int currentVersion() default 1;
    String lastModified() default "N/A";
    String lastModifiedBy() default "N/A";
    //Note use of array
    String[] reviewers();

}
