package org.nextwwpart.tan.common.classLoader;

public class WebClassLoader extends ClassLoader {
    private byte[] classData;

    private WebClassLoader() {
    }

    public WebClassLoader(byte[] classData) {
        this.classData = classData;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (classData == null || classData.length == 0) {
            throw new ClassNotFoundException("类字节码为空");
        } else {
            Class<?> returnClass = defineClass(name, classData, 0, classData.length);
            if (name.equals(returnClass.getName())) {
                return returnClass;
            } else {
                throw new ClassNotFoundException("字节码与类名不一致“");
            }
        }
    }
}
