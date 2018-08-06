
public class Test {
    
    public static void main(String[] args)
    {
        String aa = "p=12&limit=10&ul=1&order=book_view+DESC%2Cbook_uptime+DESC";
        String x[] = aa.split("\\&");
        System.out.println(x[0].split("=")[1]);
    }

}
