import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppUser } from 'app/shared/model/app-user.model';

@Component({
  selector: 'jhi-app-user-detail',
  templateUrl: './app-user-detail.component.html'
})
export class AppUserDetailComponent implements OnInit {
  appUser: IAppUser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ appUser }) => {
      this.appUser = appUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
