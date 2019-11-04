import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProvisioner, Provisioner } from 'app/shared/model/provisioner.model';
import { ProvisionerService } from './provisioner.service';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from 'app/entities/network-configuration/network-configuration.service';

@Component({
  selector: 'jhi-provisioner-update',
  templateUrl: './provisioner-update.component.html'
})
export class ProvisionerUpdateComponent implements OnInit {
  isSaving: boolean;

  networkconfigurations: INetworkConfiguration[];

  editForm = this.fb.group({
    id: [],
    uUID: [],
    provisionerName: [],
    networkConfiguration: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected provisionerService: ProvisionerService,
    protected networkConfigurationService: NetworkConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ provisioner }) => {
      this.updateForm(provisioner);
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

  updateForm(provisioner: IProvisioner) {
    this.editForm.patchValue({
      id: provisioner.id,
      uUID: provisioner.uUID,
      provisionerName: provisioner.provisionerName,
      networkConfiguration: provisioner.networkConfiguration
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const provisioner = this.createFromForm();
    if (provisioner.id !== undefined) {
      this.subscribeToSaveResponse(this.provisionerService.update(provisioner));
    } else {
      this.subscribeToSaveResponse(this.provisionerService.create(provisioner));
    }
  }

  private createFromForm(): IProvisioner {
    return {
      ...new Provisioner(),
      id: this.editForm.get(['id']).value,
      uUID: this.editForm.get(['uUID']).value,
      provisionerName: this.editForm.get(['provisionerName']).value,
      networkConfiguration: this.editForm.get(['networkConfiguration']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProvisioner>>) {
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
