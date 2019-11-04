import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRole, Role } from 'app/shared/model/role.model';
import { RoleService } from './role.service';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from 'app/entities/app-user/app-user.service';

@Component({
  selector: 'jhi-role-update',
  templateUrl: './role-update.component.html'
})
export class RoleUpdateComponent implements OnInit {
  isSaving: boolean;

  appusers: IAppUser[];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    appUser: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected roleService: RoleService,
    protected appUserService: AppUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ role }) => {
      this.updateForm(role);
    });
    this.appUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAppUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAppUser[]>) => response.body)
      )
      .subscribe((res: IAppUser[]) => (this.appusers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(role: IRole) {
    this.editForm.patchValue({
      id: role.id,
      name: role.name,
      description: role.description,
      appUser: role.appUser
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const role = this.createFromForm();
    if (role.id !== undefined) {
      this.subscribeToSaveResponse(this.roleService.update(role));
    } else {
      this.subscribeToSaveResponse(this.roleService.create(role));
    }
  }

  private createFromForm(): IRole {
    return {
      ...new Role(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      appUser: this.editForm.get(['appUser']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAppUserById(index: number, item: IAppUser) {
    return item.id;
  }
}
