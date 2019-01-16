import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/provider/user/user.service';

@Component({
  selector: 'app-userpage',
  templateUrl: './userpage.component.html',
  styleUrls: ['./userpage.component.css']
})
export class UserpageComponent implements OnInit {

  userName: any;
  userEmail: any;
  userImg: any;
  allUsers: any;
  groupName: any;
  groupDescription: any;
  groupMember: any;
  userEvents: any;
  userGroups: any[] = [];

  constructor(private _router: Router, private _userService: UserService) { }

  ngOnInit() {
    this.getUserInfo();
    this.getAllUsers();
    this.getUserGroup();
  }

  getUserInfo() {
    this.userName = localStorage.getItem('currentName');
    this.userEmail = localStorage.getItem('currentUser');
    this.userImg = localStorage.getItem('currentImg');
  }

  getAllUsers() {
    this._userService.getAllUsers().subscribe(res => {
      this.allUsers = res.json();
    });
  }

  createGroup() {
    let newGroup = {
      'groupName': this.groupName,
      'description': this.groupDescription,
      'members': this.groupMember,
      'createdBy': this.userEmail
    };
    this._userService.createGroup(newGroup).subscribe();
    window.location.reload();
  }

  getUserGroup() {
    this._userService.getUserGroup(this.userEmail).subscribe(res => {
      this.userGroups = res.json()
    });
  }

  goBack() {
    this._router.navigateByData({
      url: ['/dashboard'],
      data: 0
    });
  }

}
