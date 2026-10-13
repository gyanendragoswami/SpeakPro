import java.util.Locale;
import java.util.Scanner;

public class SpeakPro {
    private static final Scanner INPUT = new Scanner(System.in);
    private static int activitiesCompleted;
    private static int totalScore;
    private static int vocabularyCorrect;
    private static int vocabularyAsked;

    private static final String[] CONVERSATION_QUESTIONS = {
        "What is one skill you would like to improve, and why?",
        "Describe a place where you feel comfortable.",
        "What makes a good team member?",
        "Tell me about something interesting you learned recently."
    };

    private static final String[][] WORDS = {
        {"articulate", "/ɑːrˈtɪkjələt/", "able to express ideas clearly and effectively",
            "articulate an idea", "clear, expressive", "inarticulate"},
        {"meticulous", "/məˈtɪkjələs/", "very careful about details",
            "meticulous attention to detail", "thorough, precise", "careless"},
        {"concise", "/kənˈsaɪs/", "brief but clear, without unnecessary words",
            "a concise summary", "succinct, brief", "verbose"},
        {"persuasive", "/pərˈsweɪsɪv/", "able to convince someone with reasons or evidence",
            "a persuasive argument", "convincing, compelling", "unconvincing"},
        {"collaborate", "/kəˈlæbəreɪt/", "to work together with others",
            "collaborate on a project", "cooperate, partner", "compete"},
        {"resilient", "/rɪˈzɪliənt/", "able to recover after difficulty",
            "a resilient team", "adaptable, tough", "fragile"},
        {"substantial", "/səbˈstænʃəl/", "large or important enough to matter",
            "substantial progress", "significant, considerable", "minor"},
        {"clarify", "/ˈklærəfaɪ/", "to make an idea easier to understand",
            "clarify a point", "explain, illuminate", "obscure"},
        {"initiative", "/ɪˈnɪʃətɪv/", "the ability to act without waiting to be told",
            "show initiative", "enterprise, drive", "passivity"},
        {"insightful", "/ˈɪnsaɪtfəl/", "showing a deep and useful understanding",
            "an insightful observation", "perceptive, discerning", "superficial"}
    };

    private static final String[][] GRAMMAR_QUESTIONS = {
        {"She ___ to work by bus every day.", "goes",
            "Use the present simple for routines. With she/he/it, add -s or -es."},
        {"They ___ dinner right now.", "are cooking",
            "Use am/is/are + verb-ing for an action happening now."},
        {"I ___ my assignment yesterday.", "finished",
            "Use the past simple for a completed action at a finished time."},
        {"He has lived here ___ 2020.", "since",
            "Use since with a starting point in time; use for with a duration."}
    };

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("        SpeakPro English Coach");
        System.out.println("========================================");
        System.out.println("Practise English step by step. This offline chatbot");
        System.out.println("uses guided lessons and simple rules, not a live AI service.");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = prompt("Choose an option: ").trim();
            switch (choice) {
                case "1":
                    practiseConversation();
                    break;
                case "2":
                    learnVocabulary();
                    break;
                case "3":
                    grammarPractice();
                    break;
                case "4":
                    coachWriting();
                    break;
                case "5":
                    speakingPractice();
                    break;
                case "6":
                    createStudyPlan();
                    break;
                case "7":
                    showProgress();
                    break;
                case "0":
                    running = false;
                    System.out.println("Great work today. Keep practising, and see you next time!");
                    break;
                default:
                    System.out.println("Please choose a number from 0 to 7.");
            }
            System.out.println();
        }
        INPUT.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Conversation practice");
        System.out.println("2. Vocabulary lesson and quiz");
        System.out.println("3. Grammar lesson and quiz");
        System.out.println("4. Writing coach");
        System.out.println("5. Speaking prompt");
        System.out.println("6. Personal study plan");
        System.out.println("7. Progress tracker");
        System.out.println("0. Exit");
    }

    private static void practiseConversation() {
        int index = (activitiesCompleted * 3) % CONVERSATION_QUESTIONS.length;
        System.out.println("\nLet's practise. Answer in 2-4 sentences.");
        System.out.println(CONVERSATION_QUESTIONS[index]);
        String answer = prompt("> ").trim();
        if (answer.isEmpty()) {
            System.out.println("No problem. Try answering in a complete sentence next time.");
            return;
        }

        int words = wordCount(answer);
        int score = Math.min(100, 55 + Math.min(words, 30));
        System.out.println("\nThanks for sharing. Your response has about " + words + " words.");
        giveBasicWritingTips(answer);
        if (words < 8) {
            System.out.println("Try adding a reason or a specific example to develop your answer.");
        } else {
            System.out.println("Good start. To make it more persuasive, support one idea with an example.");
        }
        recordScore(score);
        System.out.println("Practice estimate: " + score + "/100 (based on response development, not a full language assessment).");
    }

    private static void learnVocabulary() {
        System.out.println("\nVocabulary builder: read each word, then write a sentence using it.");
        int correct = 0;
        for (int i = 0; i < WORDS.length; i++) {
            String[] word = WORDS[i];
            System.out.println("\n" + (i + 1) + ". " + word[0] + " " + word[1]);
            System.out.println("Meaning: " + word[2]);
            System.out.println("Collocation: " + word[3]);
            System.out.println("Synonyms: " + word[4] + " | Antonym: " + word[5]);
            String sentence = prompt("Write a sentence using \"" + word[0] + "\": ").trim();
            if (sentence.toLowerCase(Locale.ROOT).contains(word[0])) {
                System.out.println("Nice work—you used the target word.");
                correct++;
            } else if (!sentence.isEmpty()) {
                System.out.println("Try including \"" + word[0] + "\" in your sentence.");
            }
        }
        vocabularyCorrect += correct;
        vocabularyAsked += WORDS.length;
        System.out.println("\nYou used " + correct + " of " + WORDS.length + " target words.");
        recordScore(correct * 100 / WORDS.length);
        System.out.println("Tip: write a new sentence for each word tomorrow to help remember it.");
    }

    private static void grammarPractice() {
        System.out.println("\nGrammar focus: choosing verb forms from time clues.");
        System.out.println("Read the clue in each sentence, then enter the missing words.");
        int correct = 0;
        int offset = (activitiesCompleted % GRAMMAR_QUESTIONS.length);
        for (int i = 0; i < 3; i++) {
            String[] question = GRAMMAR_QUESTIONS[(offset + i) % GRAMMAR_QUESTIONS.length];
            System.out.println("\n" + question[0]);
            String answer = prompt("Your answer: ").trim();
            if (answer.equalsIgnoreCase(question[1])) {
                System.out.println("Correct. " + question[2]);
                correct++;
            } else {
                System.out.println("A good answer is \"" + question[1] + "\". " + question[2]);
            }
        }
        int score = correct * 100 / 3;
        System.out.println("\nGrammar quiz: " + correct + "/3 correct.");
        recordScore(score);
    }

    private static void coachWriting() {
        System.out.println("\nWriting coach: write a short paragraph (one line) about your day or a goal.");
        String original = prompt("> ").trim();
        if (original.isEmpty()) {
            System.out.println("Write a sentence whenever you're ready, and I can review it.");
            return;
        }

        int words = wordCount(original);
        String revised = applyCommonCorrections(original);
        int score = 100;
        if (!Character.isUpperCase(original.charAt(0))) {
            score -= 10;
        }
        if (!original.matches(".*[.!?]$")) {
            score -= 10;
        }
        if (words < 8) {
            score -= 10;
        }
        score = Math.max(40, score);

        System.out.println("\nOriginal: " + original);
        System.out.println("Suggested version: " + addEnding(revised));
        if (!revised.equals(original)) {
            System.out.println("I corrected a common phrase. Check that the suggested meaning matches what you intended.");
        }
        giveBasicWritingTips(original);
        if (words < 8) {
            System.out.println("Development tip: add a reason, detail, or example to expand your idea.");
        }
        System.out.println("Practice estimate: " + score + "/100. This checks a few basics, not every grammar or style issue.");
        recordScore(score);
    }

    private static void speakingPractice() {
        String[] prompts = {
            "Introduce yourself and describe one strength you bring to a team.",
            "Give a one-minute talk about a book, film, or idea you recommend.",
            "Explain a problem you solved and what you learned from it."
        };
        int index = activitiesCompleted % prompts.length;
        System.out.println("\nSpeaking challenge: " + prompts[index]);
        System.out.println("Speak aloud for about one minute, then type a brief summary of what you said.");
        System.out.println("I can't hear or evaluate audio here, but I can help with the text you enter.");
        String summary = prompt("> ").trim();
        if (summary.isEmpty()) {
            System.out.println("Try the speaking prompt aloud, then return and write a short summary.");
            return;
        }
        giveBasicWritingTips(summary);
        System.out.println("Speaking fluency and pronunciation are not scored without audio.");
        recordScore(Math.min(100, 60 + Math.min(wordCount(summary), 30)));
    }

    private static void createStudyPlan() {
        System.out.println("\nHow much time do you have today? Choose 30, 60, 90, or 120 minutes.");
        String choice = prompt("Minutes: ").trim();
        int minutes;
        try {
            minutes = Integer.parseInt(choice);
        } catch (NumberFormatException exception) {
            System.out.println("Please enter 30, 60, 90, or 120.");
            return;
        }
        if (minutes != 30 && minutes != 60 && minutes != 90 && minutes != 120) {
            System.out.println("Please enter 30, 60, 90, or 120.");
            return;
        }

        System.out.println("\nYour " + minutes + "-minute English plan:");
        int grammar = minutes * 15 / 100;
        int vocabulary = minutes * 15 / 100;
        int pronunciation = minutes * 10 / 100;
        int reading = minutes * 20 / 100;
        int speaking = minutes * 20 / 100;
        int writing = minutes - grammar - vocabulary - pronunciation - reading - speaking;
        printPlanItem("Grammar and sentence practice", grammar);
        printPlanItem("Vocabulary and collocations", vocabulary);
        printPlanItem("Pronunciation or read-aloud practice", pronunciation);
        printPlanItem("Reading and listening", reading);
        printPlanItem("Speaking and storytelling", speaking);
        printPlanItem("Writing and review", writing);
        System.out.println("Choose a topic you care about, and adjust the plan to your goals.");
    }

    private static void printPlanItem(String activity, int minutes) {
        System.out.println("- " + activity + ": " + minutes + " min");
    }

    private static void showProgress() {
        System.out.println("\nProgress tracker (this session only)");
        System.out.println("Activities completed: " + activitiesCompleted);
        if (activitiesCompleted == 0) {
            System.out.println("Practice score: not assessed yet.");
        } else {
            System.out.println("Average practice estimate: " + totalScore / activitiesCompleted + "/100");
        }
        if (vocabularyAsked == 0) {
            System.out.println("Vocabulary use: not assessed yet.");
        } else {
            System.out.println("Target words used in sentences: " + vocabularyCorrect + "/" + vocabularyAsked);
        }
        System.out.println("Speaking, listening, reading, and CEFR level need more evidence to estimate.");
        System.out.println("Progress is not saved after you close the program.");
    }

    private static void giveBasicWritingTips(String text) {
        if (!Character.isUpperCase(text.charAt(0))) {
            System.out.println("Tip: capitalize the first word of a sentence.");
        }
        if (!text.matches(".*[.!?]$")) {
            System.out.println("Tip: finish a complete sentence with appropriate punctuation.");
        }
        if (text.toLowerCase(Locale.ROOT).contains("i am agree")) {
            System.out.println("Grammar: say \"I agree,\" not \"I am agree.\" Agree is a verb.");
        }
        if (text.toLowerCase(Locale.ROOT).contains("discuss about")) {
            System.out.println("Usage: say \"discuss the topic,\" not \"discuss about the topic.\"");
        }
    }

    private static String applyCommonCorrections(String text) {
        String revised = text.replaceAll("(?i)\\bi am agree\\b", "I agree");
        revised = revised.replaceAll("(?i)\\bdiscuss about\\b", "discuss");
        return revised;
    }

    private static String addEnding(String text) {
        if (text.matches(".*[.!?]$")) {
            return text;
        }
        return text + ".";
    }

    private static int wordCount(String text) {
        String trimmed = text.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }
        return trimmed.split("\\s+").length;
    }

    private static void recordScore(int score) {
        totalScore += score;
        activitiesCompleted++;
    }

    private static String prompt(String message) {
        System.out.print(message);
        return INPUT.nextLine();
    }
}
