package com.deu.lab_reservation_system_android.activity.student.model

//현재 실습실 사용중인 학생 정보
class Using_lab_user {
    var lab_num: String
    var seat_id: String
    var stu_id: String
    var stu_name: String


    constructor(lab_num: String, seat_id: String, stu_id: String, stu_name: String) {
        this.lab_num = lab_num
        this.seat_id = seat_id
        this.stu_id = stu_id
        this.stu_name = stu_name
    }

}