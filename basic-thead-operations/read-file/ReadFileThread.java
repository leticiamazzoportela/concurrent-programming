import java.io.*;


/**
 *  ReadFileThread
 * @author Letícia Mazzo Portela
 * @since Mar/2020
 */
public class ReadFileThread {
    public static void main(final String[] args) throws Exception {
        final Thread t = new Thread(() -> {
            System.out.println("Start Thread " + Thread.currentThread().getName());

            while(true) {
                try {
                    final File file = new File("./quotes.txt");
                    final BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;
    
                    Thread.currentThread();
                    Thread.sleep(10000);
    
                    while ((st = br.readLine()) != null) {
                        System.out.println(st);
                    }
                    System.out.println("... Waiting 10 sec ...");
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                } catch (final FileNotFoundException e) {
                    e.printStackTrace();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    } 
}