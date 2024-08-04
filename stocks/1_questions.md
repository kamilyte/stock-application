[# Question 1

Suppose you are developing a similar (if not identical) project for a company. One teammate poses the following:

> "We do not have to worry about logging. The application is very small and tests should take care of any potential bugs. If we really need it, we can print some important data and just comment it out later."
Do you agree or disagree with the proposition? Please elaborate on your reason to agree or disagree. (~50-100 words)

___

**Answer**:
I disagree. While logging does help to trace any errors which the tests can already take care of, it is beneficial in 
the long run. It can save time in development by giving potential reasons as to why is there an error occurring, and it 
can be used to gather statistics which the company can use to better consumer experience. Overall, if any errors do occur
in the further future, logging will be able to identify it and show the potential cause of the error. 
___

# Question 2

One of your requirements is to create a mqMessage class where `key` and `value` are strings. How could you modify your class so that the key and value could be any different data types and do not require casting by the developer? Preferably, provide the code of the modified class in the answer.
___

**Answer**: The best way to do it is by using generics included in the code down below. 

```java
public final class Message1 <T, K> {
    private static int counter = 0; /*if its not static then we can't use it for each mqMessage as a unique key*/
    private final int key;
    private final T header;
    private final K value;
    private final LocalDateTime timestamp;

    public Message1 (T mHeader, K mValue) {
        key = counter;
        counter++;
        header = mHeader;
        value = mValue;
        timestamp = now();
    }
}
```

___

# Question 3

How is Continuous Integration applied to (or enforced on) your assignment? (~30-100 words)

___

**Answer**:
It is applied with the use of a shared repository GitHub, which allows for easier collaboration within teams and more 
efficient development in code. Along with code style checks in IntelliJ, it assures that code is easier to understand 
and is consistent, which makes altering code simpler. The use of tests allows us to address bugs before they lead to 
problems in the future, especially for future assignments.

___

# Question 4

One of your colleagues wrote the following class:

```java
import java.util.*;
public class MyMenu {
    private Map<Integer, PlayerAction> actions;
    public MyMenu() {
        actions = new HashMap<>();
        actions.put(0, DoNothingAction());
        actions.put(1, LookAroundAction());
        actions.put(2, FightAction());
    }
    public void printMenuOptions(boolean isInCombat) {
        List<String> menuOptions = new ArrayList<>();
        menuOptions.add("What do you want to?");
        menuOptions.add("\t0) Do nothing");
        menuOptions.add("\t1) Look around");
        if(isInCombat) {
            menuOptions.add("\t2) Fight!");
        }
    }
    public void doOption() {
        int option = getNumber();
        if(actions.containsKey(option)) {
            actions.get(option).execute();
        }
    }
    public int getNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
```
List at least 2 things that you would improve, how it relates to test-driven development and why you would improve these things. Provide the improved code below.

___

**Answer**:
I would remove the list use in printMenuOptions(), as adding the menu options to the list wont print them out unless the 
list is iterated through, and would require more testing to get rid of any possible bugs. Additionally, I would make use 
of a while loop in doOption() instead of an if statement, so the system waits until the appropriate number is pressed.
-
-

Improved code:

```java
import java.util.*;
public class MyMenu {
    private Map<Integer, PlayerAction> actions;
    public MyMenu() {
        actions = new HashMap<>();
        actions.put(0, DoNothingAction());
        actions.put(1, LookAroundAction());
        actions.put(2, FightAction());
    }
    public void printMenuOptions(boolean isInCombat) {
        System.out.println("What do you want to?");
        System.out.println("\t0) Do nothing");
        System.out.println("\t1) Look around");
        if(isInCombat) {
            System.out.println("\t2) Fight!");
        }
    }
    public void doOption() {
        int option = getNumber();
        while (!actions.containsKey(option)){
            option = getNumber();
        }
        actions.get(option).execute();
    }
    public int getNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
```
___