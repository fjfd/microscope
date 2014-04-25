package com.vipshop.microscope.common.aop;

import org.kohsuke.asm3.Label;
import org.kohsuke.asm3.MethodVisitor;
import org.kohsuke.asm3.Type;
import org.kohsuke.asm3.commons.AdviceAdapter;

public class ProbeMethodAdapter extends AdviceAdapter {

    private final String className;
    private final String methodName;
    private final Label start;
    private final Label end;

    protected ProbeMethodAdapter(MethodVisitor mv, int access, String name, String desc, String className) {
        super(mv, access, name, desc);
        start = new Label();
        end = new Label();
        methodName = name;
        this.className = className;
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        mark(end);
        catchException(start, end, Type.getType(Throwable.class));
        dup();
        push(className);
        push(methodName);
        push(methodDesc);
        loadThis();
        visitInsn(ATHROW);
        super.visitMaxs(maxStack, maxLocals);
    }

    @Override
    protected void onMethodEnter() {
        push(className);
        push(methodName);
        push(methodDesc);
        loadThis();
        loadArgArray();
        mark(start);
    }

    @Override
    protected void onMethodExit(int opcode) {
        if (opcode == ATHROW)
            return; // do nothing, @see visitMax prepareResultBy(opcode);
        push(className);
        push(methodName);
        push(methodDesc);
        loadThis();
    }

}