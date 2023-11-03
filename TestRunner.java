import java.lang.reflect.Method;

public class TestRunner {

    public void runTests(Class<?> testClass) {

        Method[] methods = testClass.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                Test test = method.getAnnotation(Test.class);
                if (test.enabled()) {
                    System.out.println("Running test: " + test.name());
                    try {
                        method.invoke(testClass.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
