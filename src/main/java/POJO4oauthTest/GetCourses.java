package POJO4oauthTest;

public class GetCourses {

    //  when you launch oAuthTest, the courses are extracted as JSON like below:
    /*  use the like --->    https://jsoneditoronline.org/     to understand the JSON Structure and the nested objects
    WE HAVE six <k,v> so let's create 6  variables  (courses has nested objects)
    This GetCourses class is the main parent object, courses class is the child. courses is a child of GetCourses
    but he has three child (webApplication, Mobile, Api), so must create three other classes

    {
  "instructor": "RahulShetty",
  "url": "rahulshettycademy.com",
  "services": "projectSupport",
  "expertise": "Automation",
  "courses": {
    "webAutomation": [
      {
        "courseTitle": "Selenium Webdriver Java",
        "price": "50"
      },
      {
        "courseTitle": "Cypress",
        "price": "40"
      },
      {
        "courseTitle": "Protractor",
        "price": "40"
      }
    ],
    "api": [
      {
        "courseTitle": "Rest Assured Automation using Java",
        "price": "50"
      },
      {
        "courseTitle": "SoapUI Webservices testing",
        "price": "40"
      }
    ],
    "mobile": [
      {
        "courseTitle": "Appium-Mobile Automation using Java",
        "price": "50"
      }
    ]
  },
  "linkedIn": "https://www.linkedin.com/in/rahul-shetty-trainer/"
}

    */

    private String instructor;
    private String url;
    private String services;
    private String expertise;
    private Courses courses;
    private String linkedIn;


    public String getInstructor() {
        return instructor;
    }

    public String getUrl() {
        return url;
    }

    public String getServices() {
        return services;
    }

    public String getExpertise() {
        return expertise;
    }

    public Courses getCourses() {
        return courses;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }






}
