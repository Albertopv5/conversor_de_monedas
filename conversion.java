import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class conversion {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);


            String apiKey = "6b1677ab72bad78597c3fd8e";
            // Moneda base
            String monedaBase = "MXN";

            do {
                // Construye la URL para obtener las tasas de cambio
                String urlStr = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + monedaBase;
                URL url = new URL(urlStr);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();

                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader(request.getInputStream()));
                JsonObject jsonobj = root.getAsJsonObject();

                // Accede a las tasas de conversión
                JsonObject conversionRates = jsonobj.getAsJsonObject("conversion_rates");

                // menú de opciones
                System.out.println("Seleccione una opción:");
                System.out.println("1. Convertir de MXN a USD");
                System.out.println("2. Convertir de MXN a EUR");
                System.out.println("3. Convertir de USD a EUR");
                System.out.println("4. Salir");
                int opcion = scanner.nextInt();

                if (opcion == 4) {
                    System.out.println("!Gracias por usar mi conversor!");
                    break;
                }


                System.out.print("Ingrese el valor a convertir: ");
                double valor = scanner.nextDouble();

                switch (opcion) {
                    case 1:
                        double mxnAUsd = conversionRates.get("USD").getAsDouble();
                        double resultadoUsd = valor * mxnAUsd;
                        System.out.println("Resultado en USD: " + resultadoUsd);
                        break;

                    case 2:
                        double mxnAEur = conversionRates.get("EUR").getAsDouble();
                        double resultadoEur = valor * mxnAEur;
                        System.out.println("Resultado en EUR: " + resultadoEur);
                        break;

                    case 3:
                        double usdAEur = conversionRates.get("EUR").getAsDouble() / conversionRates.get("USD").getAsDouble();
                        double resultadoEurConUsd = valor * usdAEur;
                        System.out.println("Resultado en EUR: " + resultadoEurConUsd);
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } while (true);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
