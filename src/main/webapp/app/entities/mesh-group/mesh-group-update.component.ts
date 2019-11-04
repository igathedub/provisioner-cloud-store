import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMeshGroup, MeshGroup } from 'app/shared/model/mesh-group.model';
import { MeshGroupService } from './mesh-group.service';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from 'app/entities/network-configuration/network-configuration.service';

@Component({
  selector: 'jhi-mesh-group-update',
  templateUrl: './mesh-group-update.component.html'
})
export class MeshGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  networkconfigurations: INetworkConfiguration[];

  editForm = this.fb.group({
    id: [],
    name: [],
    address: [],
    parentAddress: [],
    networkConfiguration: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected meshGroupService: MeshGroupService,
    protected networkConfigurationService: NetworkConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ meshGroup }) => {
      this.updateForm(meshGroup);
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

  updateForm(meshGroup: IMeshGroup) {
    this.editForm.patchValue({
      id: meshGroup.id,
      name: meshGroup.name,
      address: meshGroup.address,
      parentAddress: meshGroup.parentAddress,
      networkConfiguration: meshGroup.networkConfiguration
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const meshGroup = this.createFromForm();
    if (meshGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.meshGroupService.update(meshGroup));
    } else {
      this.subscribeToSaveResponse(this.meshGroupService.create(meshGroup));
    }
  }

  private createFromForm(): IMeshGroup {
    return {
      ...new MeshGroup(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      address: this.editForm.get(['address']).value,
      parentAddress: this.editForm.get(['parentAddress']).value,
      networkConfiguration: this.editForm.get(['networkConfiguration']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeshGroup>>) {
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
