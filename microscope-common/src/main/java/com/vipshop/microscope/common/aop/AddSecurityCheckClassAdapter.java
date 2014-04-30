package com.vipshop.microscope.common.aop;

import org.kohsuke.asm3.ClassAdapter;
import org.kohsuke.asm3.ClassVisitor;
import org.kohsuke.asm3.MethodVisitor;

class AddSecurityCheckClassAdapter extends ClassAdapter {

    public AddSecurityCheckClassAdapter(ClassVisitor cv) {
        //Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
        // 负责改写后代码的输出
        super(cv);
    }

    // 重写 visitMethod，访问到 "operation" 方法时，
    // 给出自定义 MethodVisitor，实际改写方法内容
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        if (mv != null) {
            // 对于 "operation" 方法
            if (name.equals("operation")) {
                // 使用自定义 MethodVisitor，实际改写方法内容
//                System.out.println("check operation");
                Checker.check();
            }
        }
        return wrappedMv;
    }

}