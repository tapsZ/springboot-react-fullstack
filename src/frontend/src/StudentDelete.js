// StudentDelete.js

import {deleteStudent} from "./client";
import {useState} from 'react';
import {successNotification} from "./notification";

function StudentDelete() {
        const [submitting, setSubmitting]=  useState(false);

    const onFinish = student => {
        setSubmitting(true);
        console.log(JSON.stringify(student, null, 2));
        deleteStudent(student.id)
        .then(()=>{
            console.log("student deleted");
            onCLose();
            successNotification("Student Successfully Deleted", `${student.name} was added to the system`)
            fetchStudents();
        }).catch(err =>{
            console.log(err);
        }).finally(()=>{
            setSubmitting(false);
        })
    };

    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2));
    };


}

export default StudentDelete;