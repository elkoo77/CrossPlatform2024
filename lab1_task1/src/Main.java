public class Main {
    public static void main(String[] args) {
        // Наприклад, аналіз класу java.util.ArrayList
        String className = "java.util.ArrayList";
        String analysisResult = ClassAnalyzer.analyzeClass(className);
        System.out.println(analysisResult);
    }
}
