import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INetKey, NetKey } from 'app/shared/model/net-key.model';
import { NetKeyService } from './net-key.service';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from 'app/entities/network-configuration/network-configuration.service';

@Component({
  selector: 'jhi-net-key-update',
  templateUrl: './net-key-update.component.html'
})
export class NetKeyUpdateComponent implements OnInit {
  isSaving: boolean;

  networkconfigurations: INetworkConfiguration[];

  editForm = this.fb.group({
    id: [],
    phase: [],
    minSecurity: [],
    key: [],
    timestamp: [],
    name: [],
    index: [],
    networkConfiguration: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected netKeyService: NetKeyService,
    protected networkConfigurationService: NetworkConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ netKey }) => {
      this.updateForm(netKey);
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

  updateForm(netKey: INetKey) {
    this.editForm.patchValue({
      id: netKey.id,
      phase: netKey.phase,
      minSecurity: netKey.minSecurity,
      key: netKey.key,
      timestamp: netKey.timestamp,
      name: netKey.name,
      index: netKey.index,
      networkConfiguration: netKey.networkConfiguration
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const netKey = this.createFromForm();
    if (netKey.id !== undefined) {
      this.subscribeToSaveResponse(this.netKeyService.update(netKey));
    } else {
      this.subscribeToSaveResponse(this.netKeyService.create(netKey));
    }
  }

  private createFromForm(): INetKey {
    return {
      ...new NetKey(),
      id: this.editForm.get(['id']).value,
      phase: this.editForm.get(['phase']).value,
      minSecurity: this.editForm.get(['minSecurity']).value,
      key: this.editForm.get(['key']).value,
      timestamp: this.editForm.get(['timestamp']).value,
      name: this.editForm.get(['name']).value,
      index: this.editForm.get(['index']).value,
      networkConfiguration: this.editForm.get(['networkConfiguration']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INetKey>>) {
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
