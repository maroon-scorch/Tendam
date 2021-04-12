import React, { useState, useEffect } from 'react'
import { useAuth } from "../../context/AuthContext";
import { useDatabase } from "../../context/DatabaseContext";

function Message({ history }) {
    const { currentUser } = useAuth();
    const { getFile } = useDatabase();

    const [profileInfo, setProfileInfo] = useState({
        bio: "", name: "", age: "", id: "", matches: []});
    const [profilePic, setProfilePic] = useState("blank-profile.png");

    useEffect(async () => {
        let userData = history.location.state;
        console.log(userData);
        if (typeof userData !== "undefined") {
            setProfileInfo(userData.data);

            getFile(userData.data.id, '/profilePicture').then((imageFile) => {
                setProfilePic(imageFile);
            }).catch((error) => {
                // console.log("Error getting document:", error);
            });
        }
    }, []);


    return (
        <div>
           {"From: " + currentUser.uid}
           <br />
           {/* {JSON.stringify(profileInfo)}  */}
           {"To: " + profileInfo.id}
        </div>
    )
}

export default Message
