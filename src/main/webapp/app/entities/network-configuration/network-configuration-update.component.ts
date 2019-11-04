import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INetworkConfiguration, NetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from './network-configuration.service';
import { IFacility } from 'app/shared/model/facility.model';
import { FacilityService } from 'app/entities/facility/facility.service';

@Component({
  selector: 'jhi-network-configuration-update',
  templateUrl: './network-configuration-update.component.html'
})
export class NetworkConfigurationUpdateComponent implements OnInit {
  isSaving: boolean;

  facilities: IFacility[];

  editForm = this.fb.group({
    id: [],
    meshUUID: [],
    timestamp: [],
    meshName: [],
    facility: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected networkConfigurationService: NetworkConfigurationService,
    protected facilityService: FacilityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ networkConfiguration }) => {
      this.updateForm(networkConfiguration);
    });
    this.facilityService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFacility[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFacility[]>) => response.body)
      )
      .subscribe((res: IFacility[]) => (this.facilities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(networkConfiguration: INetworkConfiguration) {
    this.editForm.patchValue({
      id: networkConfiguration.id,
      meshUUID: networkConfiguration.meshUUID,
      timestamp: networkConfiguration.timestamp,
      meshName: networkConfiguration.meshName,
      facility: networkConfiguration.facility
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const networkConfiguration = this.createFromForm();
    if (networkConfiguration.id !== undefined) {
      this.subscribeToSaveResponse(this.networkConfigurationService.update(networkConfiguration));
    } else {
      this.subscribeToSaveResponse(this.networkConfigurationService.create(networkConfiguration));
    }
  }

  private createFromForm(): INetworkConfiguration {
    return {
      ...new NetworkConfiguration(),
      id: this.editForm.get(['id']).value,
      meshUUID: this.editForm.get(['meshUUID']).value,
      timestamp: this.editForm.get(['timestamp']).value,
      meshName: this.editForm.get(['meshName']).value,
      facility: this.editForm.get(['facility']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INetworkConfiguration>>) {
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

  trackFacilityById(index: number, item: IFacility) {
    return item.id;
  }
}
