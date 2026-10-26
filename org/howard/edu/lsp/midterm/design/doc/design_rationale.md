# Design Rationale – Evolving Requirements

## How the design accommodates the new requirements

In the Part 2 design, `RegistrationService` never talks to a file or an email system directly. It talks to the `RegistrationRepository` and `ConfirmationNotifier` contracts, so supporting new storage and delivery options means adding classes, not editing the workflow.

**Storage (file or database).** `FileRegistrationRepository` already implements `RegistrationRepository`. I would add one new card, `DatabaseRegistrationRepository`, with the responsibility "save a Registration to a database" and the collaborator `Registration`. `RegistrationService`, `Registration`, and `TuitionCalculator` do not change.

**Notification (email or text message).** `EmailNotifier` already implements `ConfirmationNotifier`. I would add `SmsNotifier`, with the responsibility "send a confirmation by text message to the student's phone number," collaborating with `Student` and `Registration`.

## Changes to the CRC cards

1. **Student** gains a responsibility: store a phone number (needed for text messages).
2. **New card: DatabaseRegistrationRepository** (implements `RegistrationRepository`), collaborator: `Registration`.
3. **New card: SmsNotifier** (implements `ConfirmationNotifier`), collaborators: `Student`, `Registration`.
4. **New card: RegistrationConfiguration.**
   - Responsibilities: read the deployment environment settings, and choose which repository and which notifier the service uses.
   - Collaborators: `RegistrationRepository`, `ConfirmationNotifier`, `RegistrationService`.
5. **RegistrationService** is unchanged in its responsibilities. Its collaborators are still the repository and notifier contracts; it is simply handed whichever implementations were chosen.

## Key decision: who owns the choice of storage and notification?

I assign the responsibility "decide which repository and notifier to use" to `RegistrationConfiguration`, **not** to `RegistrationService`.

**Why:**
- `RegistrationService`'s key abstraction is *coordinating the registration workflow* (Riel 2.8). If it also decided between file and database, or email and text, it would need `if` statements on environment and channel, which is the same problem the original class had, just moved.
- Choosing implementations is a deployment concern that changes for different reasons (environment, customer preference) than the workflow does. Giving it its own owner keeps each class to one reason to change.
- With the choice made outside the service, adding a third storage option or a push-notification channel touches only the new implementation class and the configuration card. The workflow, tuition rules, and `Registration` stay untouched, and the service can be tested with a fake repository and a fake notifier.

This is consistent with Part 2: `RegistrationService` still collaborates only with the `RegistrationRepository` and `ConfirmationNotifier` contracts, as listed on its original card.
