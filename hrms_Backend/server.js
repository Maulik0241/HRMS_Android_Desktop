'use strict'

const firebaseApp = require("firebase/app")

const morgan = require("morgan")

const {getAuth,signInWithEmailAndPassword,createUserWithEmailAndPassword} = require("firebase/auth")


const firebaseConfig = {
    apiKey: "AIzaSyAlCfCjNR9ZGnTN_7pSPZ9aPJ8eDxrnvMs",
    authDomain: "hrmsbackend-9b454.firebaseapp.com",
    projectId: "hrmsbackend-9b454",
    storageBucket: "hrmsbackend-9b454.appspot.com",
    messagingSenderId: "509066348324",
    appId: "1:509066348324:web:45cf96a70f9243772495b1"
  };

  
  const auth = getAuth(firebaseApp.initializeApp(firebaseConfig));

  const cors = require('cors')

  const express = require('express')
  const app = express()

  const admin = require("firebase-admin")
  const credentials  = require("./serviceAccountKey.json");

  admin.initializeApp({
    credential:admin.credential.cert(credentials)
  })

  const db = admin.firestore()

  app.use(cors())
  app.use(morgan("combined"))
  app.use(express.json())
  app.use(express.urlencoded({extended:true}))





  app.get("/",(req,res)=>{
      res.send("Hello this node backend server index page")
  })



  // Signed in
  app.post("/signIn", (req, res) => {
    const email = req.query.email;
    const password = req.query.password;
  
    signInWithEmailAndPassword(auth, email, password)
      .then(async (userCredential) => {
        const user = userCredential.user;
        const uid = user.uid;
        const userEmail = user.email;
  
        // Fetch the user profile from the UserProfiles collection
        const userProfileDoc = await db.collection("UserProfiles").doc(uid).get();
        const userProfile = userProfileDoc.data();
        
        if (userProfile && userProfile.isUser === "Admin") {
          res.send({ uid, email: userEmail });
        } else {
          res.status(401).json({ error: "Unauthorized" });
        }
      })
      .catch((error) => {
        const errorCode = error.code;
        const errorMessage = error.message;
        res.status(401).json({ error: errorCode });
      });
  });
  
  
  app.get("/getUsers", (req, res) => {
    // Retrieve all user details from the User collection
    db.collection("UserProfiles").get()
      .then((snapshot) => {
        if (snapshot.empty) {
          res.status(404).send("No users found.");
        } else {
          const users = [];
          snapshot.forEach((doc) => {
            const user = { id: doc.id, ...doc.data() }; // Include the id field in the user object
            users.push(user);
          });
          res.send(users);
        }
      })
      .catch((error) => {
        console.error("Error retrieving users:", error);
        res.status(500).send("Error retrieving users.");
      });
  });

  
app.get("/getUser/:id", (req, res) => {
  // Assuming user ID is provided as a URL parameter
     // Retrieve user details from the User collection
     db.collection("UserProfiles").doc(req.params.id).get()
       .then((doc) => {
         if (doc.exists) {
           const user = doc.data();
           res.send(user);
         } else {
           res.status(404).send("User not found.");
         }
       })
       .catch((error) => {
         console.error("Error retrieving user:", error);
         res.status(500).send("Error retrieving user.");
       });
   });

   app.post("/updateUserProfile/:id", (req, res) => {
    const userId = req.params.id;
    const { name, email, contactNumber, role } = req.body;
  
    // Update user details in the UserProfiles collection
    db.collection("UserProfiles").doc(userId).update({
      name,
      email,
      contactNumber,
      role,
    })
      .then(() => {
        res.send("User profile updated successfully.");
      })
      .catch((error) => {
        console.error("Error updating user profile:", error);
        res.status(500).send("Error updating user profile.");
      });
  });
  


  app.delete("/deleteUser/:id", async (req, res) => {
    try {
      const userId = req.params.id;
      
      // Delete the user profile document
      await db.collection("UserProfiles").doc(userId).delete();
  
      // Delete the user's authentication ID
      await admin.auth().deleteUser(userId);
  
      res.send("User and Profile Deleted Successfully");
    } catch (error) {
      res.send(error);
    }
  });
  





  //Announcements Api


    app.post("/addAnnouncement", async (req, res) => {
      try {
        const addAnnouncement = {
          description: req.body.description,
          createdOn: new Date().toLocaleString("en-IN", {
            timeZone: "Asia/Kolkata",
            dateStyle: "medium",
            timeStyle: "medium"
          })
        };
        const docRef = await db.collection("Annoucements").add(addAnnouncement)
        const response = { id:docRef.id }
        res.send(response)
      } catch (error) {
        res.send(error)
      }
    });

    app.get("/getAnnouncement",async(req,res)=>{
      try{
        const response = await db.collection("Annoucements").get()
        const responseArr = [];

        response.forEach((doc)=>{
          responseArr.push({ 
            id:doc.id,
            ...doc.data()
          })
        })

        res.send(responseArr)
      }catch(error){
        res.send(error)
      }
    })

    app.post("/updateAnnouncement", async (req, res) => {
      try {
        const id = req.body.id;
        const updatedAnnouncement = {
          description: req.body.description,
          updatedOn: new Date().toLocaleString("en-IN", {
            timeZone: "Asia/Kolkata",
            dateStyle: "medium",
            timeStyle: "medium"
          })
        };
        const announcementRef = db.collection("Annoucements").doc(id);
        await announcementRef.update(updatedAnnouncement);
        res.send("Announcement updated successfully");
      } catch (error) {
        res.send(error);
      }
    });

    app.delete("/deleteAnnouncement/:id",async(req,res)=>{
      try{
        const response = await db.collection("Annoucements").doc(req.params.id).delete();
        res.send("Announcement Deleted Successfully");
      }catch(error){
        res.send(error)
      }
    })





    //Holiday Management Api

    app.get("/getHolidays", async (req, res) => {
      try {
        const response = await db.collection("Holidays").get();
        const responseArr = [];
    
        response.forEach((doc) => {
          responseArr.push({
            id: doc.id,
            ...doc.data(),
          });
        });
    
        res.send(responseArr);
      } catch (error) {
        res.send(error);
      }
    });
    
    app.post("/addHoliday", async (req, res) => {
      try {
        const dateParts = req.body.date.split("/"); // Assuming the date is provided as "dd/mm/yyyy"
        const day = parseInt(dateParts[0], 10);
        const month = parseInt(dateParts[1], 10) - 1; // Months are zero-based in JavaScript Date
        const year = parseInt(dateParts[2], 10);
    
        const holidayData = {
          date: req.body.date,
          description: req.body.description,
        };
    
        const holidayRef = await db.collection("Holidays").add(holidayData);
        res.send("Holiday added successfully");
      } catch (error) {
        res.send(error);
      }
    });
    
    app.put("/updateHoliday/:id", async (req, res) => {
      try {
        const id = req.params.id;
        const dateParts = req.body.date.split("/"); // Assuming the date is provided as "dd/mm/yyyy"
        const day = parseInt(dateParts[0], 10);
        const month = parseInt(dateParts[1], 10) - 1; // Months are zero-based in JavaScript Date
        const year = parseInt(dateParts[2], 10);
    
        const updatedHoliday = {
          date: req.body.date,
          description: req.body.description,
        };
    
        const holidayRef = db.collection("Holidays").doc(id);
        await holidayRef.update(updatedHoliday);
        res.send("Holiday updated successfully");
      } catch (error) {
        res.send(error);
      }
    });
    
    app.delete("/deleteHoliday/:id", async (req, res) => {
      try {
        const response = await db.collection("Holidays").doc(req.params.id).delete();
        res.send("Holiday deleted successfully");
      } catch (error) {
        res.send(error);
      }
    });


    //Project Management

    app.get("/getProjects", async (req, res) => {
      try {
        const response = await db.collection("Projects").get();
        const responseArr = [];
    
        response.forEach((doc) => {
          responseArr.push({
            id: doc.id,
            name: doc.data().name,
          });
        });
    
        res.send(responseArr);
      } catch (error) {
        res.send(error);
      }
    });
    
    app.post("/addProject", async (req, res) => {
      try {
        const projectData = {
          name: req.body.name,
        };
    
        const projectRef = await db.collection("Projects").add(projectData);
        res.send("Project added successfully");
      } catch (error) {
        res.send(error);
      }
    });
    
    app.put("/updateProject/:id", async (req, res) => {
      try {
        const id = req.params.id;
    
        const updatedProject = {
          name: req.body.name,
        };
    
        const projectRef = db.collection("Projects").doc(id);
        await projectRef.update(updatedProject);
        res.send("Project updated successfully");
      } catch (error) {
        res.send(error);
      }
    });
    
    app.delete("/deleteProject/:id", async (req, res) => {
      try {
        const response = await db.collection("Projects").doc(req.params.id).delete();
        res.send("Project deleted successfully");
      } catch (error) {
        res.send(error);
      }
    });
    
        
    

  const PORT = 3000




  app.listen(PORT,()=>{

      console.log(`Server is running on port:${PORT}`)

  })
