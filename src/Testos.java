public class Testos {
    public static void main(String[] args) {
        System.out.println(new Ex().opa());
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
