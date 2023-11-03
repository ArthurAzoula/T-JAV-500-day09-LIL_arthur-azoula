import java.lang.reflect.Method;

public class TestRunner {

    public void runTests(Class<?> testClass) throws Exception {
        Object testObject = testClass.getDeclaredConstructor().newInstance();
        Method[] methods = testClass.getDeclaredMethods();

        // Execute @BeforeClass method if exists
        executeBeforeClass(testClass, testObject);

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                Test test = method.getAnnotation(Test.class);
                if (test.enabled()) {
                    // Execute @Before method before each test
                    executeBefore(testClass, testObject);

                    System.out.println("Running test: " + test.name());
                    try {
                        method.invoke(testObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Execute @After method after each test
                    executeAfter(testClass, testObject);
                }
            }
        }

        // Execute @AfterClass method if exists
        executeAfterClass(testClass, testObject);
    }

    private void executeBeforeClass(Class<?> testClass, Object testObject) throws Exception {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeClass.class)) {
                method.invoke(testObject);
                break;
            }
        }
    }

    private void executeAfterClass(Class<?> testClass, Object testObject) throws Exception {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterClass.class)) {
                method.invoke(testObject);
                break;
            }
        }
    }

    private void executeBefore(Class<?> testClass, Object testObject) throws Exception {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) {
                method.invoke(testObject);
                break;
            }
        }
    }

    private void executeAfter(Class<?> testClass, Object testObject) throws Exception {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(After.class)) {
                method.invoke(testObject);
                break;
            }
        }
    }
}
