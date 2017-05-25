public class Testos {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++)
            System.out.println((Math.random() + 1) / 2);
    }

    public static class Ex {
        public double opa() {
            try {
                throw new Exception();
            } catch (Exception e) {
                try {
                    throw new Exception();
                } catch (Exception i) {
                    return 2.2;
                } finally {
                    return 2.3;
                }
            } finally {
                return 3;
            }
        }
    }
}
