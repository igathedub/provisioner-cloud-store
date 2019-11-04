import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAppKey, AppKey } from 'app/shared/model/app-key.model';
import { AppKeyService } from './app-key.service';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from 'app/entities/network-configuration/network-configuration.service';

@Component({
  selector: 'jhi-app-key-update',
  templateUrl: './app-key-update.component.html'
})
export class AppKeyUpdateComponent implements OnInit {
  isSaving: boolean;

  networkconfigurations: INetworkConfiguration[];

  editForm = this.fb.group({
    id: [],
    key: [],
    name: [],
    boundNetKey: [],
    index: [],
    networkConfiguration: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected appKeyService: AppKeyService,
    protected networkConfigurationService: NetworkConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ appKey }) => {
      this.updateForm(appKey);
    });
    this.networkConfigurationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INetworkConfiguration[]>) => mayBeOk.ok),
        map((response: HttpResponse<INetworkConfiguration[]>) => response.body)
      )
      .subscribe(
        (res: INetworkConfiguration[]) => (this.networkconfigurations = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(appKey: IAppKey) {
    this.editForm.patchValue({
      id: appKey.id,
      key: appKey.key,
      name: appKey.name,
      boundNetKey: appKey.boundNetKey,
      index: appKey.index,
      networkConfiguration: appKey.networkConfiguration
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const appKey = this.createFromForm();
    if (appKey.id !== undefined) {
      this.subscribeToSaveResponse(this.appKeyService.update(appKey));
    } else {
      this.subscribeToSaveResponse(this.appKeyService.create(appKey));
    }
  }

  private createFromForm(): IAppKey {
    return {
      ...new AppKey(),
      id: this.editForm.get(['id']).value,
      key: this.editForm.get(['key']).value,
      name: this.editForm.get(['name']).value,
      boundNetKey: this.editForm.get(['boundNetKey']).value,
      index: this.editForm.get(['index']).value,
      networkConfiguration: this.editForm.get(['networkConfiguration']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppKey>>) {
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

  trackNetworkConfigurationById(index: number, item: INetworkConfiguration) {
    return item.id;
  }
}
