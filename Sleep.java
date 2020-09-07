public class Sleep {
    public void sayHi() throws InterruptedException {
        for (int i = 0; i < 10; i++){
            System.out.println("Hi");
            Thread.sleep(2000);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Sleep s = new Sleep();
        s.sayHi();
    }
}
