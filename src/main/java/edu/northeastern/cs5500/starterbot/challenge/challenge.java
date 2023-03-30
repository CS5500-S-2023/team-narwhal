public interface challenge {
    // different types of challenge could have different fields
    // e.g. captcha should have a correct answer, email should a status?

    // a challenge can be CAPTCHA, email, SMS
    // what's the difference between a challenge and a authentication?
    // having a challenge controller: 1. creates a challenge 2. keep track of the correct answer 
    // 3. keep track of the user answer 4. the number of attemps that the user has tried

}