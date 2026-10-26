# Design Evaluation – `CourseRegistration`

`CourseRegistration` works for a toy example, but its structure violates several of Arthur Riel's object-oriented design heuristics and would be hard to maintain and extend.

## 1. One class, many abstractions (Riel 2.8, 3.2)

Riel's heuristic 2.8 says a class should capture **one and only one key abstraction**. `registerStudent()` does at least six unrelated jobs: it calculates tuition, applies a discount policy, prints a confirmation, writes to a file, "sends" an email, and logs. Student identity, course data, pricing rules, persistence, notification, and logging are all different abstractions. This is close to the "god class" that heuristic 3.2 warns against: one class holding the intelligence for the whole workflow.

**Consequence:** a change to any one concern (a new discount rule, a different file format, a new email provider) forces edits to the same method, which risks breaking the others. The class has many reasons to change, and it cannot be tested one concern at a time.

## 2. Public data, no encapsulation (Riel 2.1)

Heuristic 2.1 says all data in a class should be hidden within that class. All five fields (`studentId`, `studentName`, `email`, `courseCode`, `creditHours`) are `public`. Any code can set `creditHours` to a negative number or `email` to `null`, and the class cannot protect its own invariants.

**Consequence:** correctness cannot be guaranteed. Every client depends on the internal representation, so renaming or retyping a field breaks all users.

## 3. Related data and behavior are not kept together (Riel 2.9)

Heuristic 2.9 says to keep related data and behavior in one place. Tuition is a function of credit hours and pricing policy, yet the pricing rule is buried inside a registration method rather than owned by an object responsible for it. Meanwhile, student data (`studentId`, `studentName`, `email`) is flattened into a registration class instead of belonging to a `Student`.

## 4. Intelligence is concentrated, not distributed (Riel 3.1)

Heuristic 3.1 says to distribute system intelligence horizontally as uniformly as possible. Here nearly all behavior sits in one method, and the "collaborating" objects are just raw strings and numbers. Nothing else in the system has meaningful responsibilities.

## 5. Hard-coded dependencies hurt extensibility

- `"registrations.txt"` is hard-wired, so switching to a database means rewriting the class.
- `System.out.println("Sending confirmation email...")` fakes email and hard-wires the delivery channel, so adding text messages means more conditionals in the same method.
- Magic numbers (`750.0`, `12`, `0.90`) are embedded in logic, so tuition policy cannot change without editing registration code.

The class depends on concrete details (file system, console, `Date`) rather than on abstractions, so it cannot be reused or unit-tested without real I/O side effects.

## 6. Correctness and robustness problems

- `catch (Exception e) { e.printStackTrace(); }` swallows failures: if the file write fails, the method still "sends" a confirmation and logs success, so the student is told they are registered when no record exists.
- `FileWriter` is closed manually, so an exception during `write` leaks the file handle (try-with-resources would fix this).
- Money is stored as `double`, which is imprecise for currency.
- The confirmation and logging output are mixed with business logic, and the class prints the tuition as a raw double.
- A `registerStudent()` method with no parameters and no return value tells callers nothing about success or failure.

## 7. Naming and role

The class is named `CourseRegistration` (a noun for a registration record), but behaves like a procedural "processor." Riel also cautions against classes that are really functions in disguise: the behavior here is a script, not the responsibility of an object.

## Summary

| Problem | Riel heuristic | Effect |
|---|---|---|
| Six jobs in one method | 2.8, 3.2 | Hard to change or test |
| Public fields | 2.1 | No invariants, tight coupling |
| Tuition and student data misplaced | 2.9 | Logic scattered or buried |
| All intelligence in one place | 3.1 | Poor distribution of responsibility |
| Hard-coded file, email, rates | (dependency on concrete details) | Not extensible |
| Swallowed exceptions, `double` money | (correctness) | Silent failures |

The fix is to split these responsibilities into focused collaborating classes, as proposed in `design.md`.
