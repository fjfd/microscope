package com.vipshop.microscope.common.aop;

import java.io.File;
import java.io.FileOutputStream;

import org.kohsuke.asm3.ClassAdapter;
import org.kohsuke.asm3.ClassReader;
import org.kohsuke.asm3.ClassWriter;

public class Generator {

    public static void main() throws Exception {
        ClassReader cr = new ClassReader("Account");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file = new File("Account.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
    }

}