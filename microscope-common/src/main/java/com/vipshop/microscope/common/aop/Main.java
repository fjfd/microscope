package com.vipshop.microscope.common.aop;

import org.kohsuke.asm3.ClassAdapter;
import org.kohsuke.asm3.ClassReader;
import org.kohsuke.asm3.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassReader cr = new ClassReader("Account");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file = new File("E:\\Workspace\\microscope\\microscope-common\\target\\classes\\Account.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
        Account account = new Account();
        account.operation();
    }
}