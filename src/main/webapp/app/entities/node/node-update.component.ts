import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INode, Node } from 'app/shared/model/node.model';
import { NodeService } from './node.service';
import { IFeatures } from 'app/shared/model/features.model';
import { FeaturesService } from 'app/entities/features/features.service';
import { INetworkConfiguration } from 'app/shared/model/network-configuration.model';
import { NetworkConfigurationService } from 'app/entities/network-configuration/network-configuration.service';

@Component({
  selector: 'jhi-node-update',
  templateUrl: './node-update.component.html'
})
export class NodeUpdateComponent implements OnInit {
  isSaving: boolean;

  features: IFeatures[];

  networkconfigurations: INetworkConfiguration[];

  editForm = this.fb.group({
    id: [],
    unicastAddress: [],
    configComplete: [],
    defaultTTL: [],
    cid: [],
    blacklisted: [],
    uUID: [],
    security: [],
    crpl: [],
    name: [],
    deviceKey: [],
    vid: [],
    pid: [],
    features: [],
    networkConfiguration: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected nodeService: NodeService,
    protected featuresService: FeaturesService,
    protected networkConfigurationService: NetworkConfigurationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ node }) => {
      this.updateForm(node);
    });
    this.featuresService
      .query({ filter: 'node-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IFeatures[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFeatures[]>) => response.body)
      )
      .subscribe(
        (res: IFeatures[]) => {
          if (!this.editForm.get('features').value || !this.editForm.get('features').value.id) {
            this.features = res;
          } else {
            this.featuresService
              .find(this.editForm.get('features').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IFeatures>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IFeatures>) => subResponse.body)
              )
              .subscribe(
                (subRes: IFeatures) => (this.features = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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

  updateForm(node: INode) {
    this.editForm.patchValue({
      id: node.id,
      unicastAddress: node.unicastAddress,
      configComplete: node.configComplete,
      defaultTTL: node.defaultTTL,
      cid: node.cid,
      blacklisted: node.blacklisted,
      uUID: node.uUID,
      security: node.security,
      crpl: node.crpl,
      name: node.name,
      deviceKey: node.deviceKey,
      vid: node.vid,
      pid: node.pid,
      features: node.features,
      networkConfiguration: node.networkConfiguration
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const node = this.createFromForm();
    if (node.id !== undefined) {
      this.subscribeToSaveResponse(this.nodeService.update(node));
    } else {
      this.subscribeToSaveResponse(this.nodeService.create(node));
    }
  }

  private createFromForm(): INode {
    return {
      ...new Node(),
      id: this.editForm.get(['id']).value,
      unicastAddress: this.editForm.get(['unicastAddress']).value,
      configComplete: this.editForm.get(['configComplete']).value,
      defaultTTL: this.editForm.get(['defaultTTL']).value,
      cid: this.editForm.get(['cid']).value,
      blacklisted: this.editForm.get(['blacklisted']).value,
      uUID: this.editForm.get(['uUID']).value,
      security: this.editForm.get(['security']).value,
      crpl: this.editForm.get(['crpl']).value,
      name: this.editForm.get(['name']).value,
      deviceKey: this.editForm.get(['deviceKey']).value,
      vid: this.editForm.get(['vid']).value,
      pid: this.editForm.get(['pid']).value,
      features: this.editForm.get(['features']).value,
      networkConfiguration: this.editForm.get(['networkConfiguration']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INode>>) {
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

  trackFeaturesById(index: number, item: IFeatures) {
    return item.id;
  }

  trackNetworkConfigurationById(index: number, item: INetworkConfiguration) {
    return item.id;
  }
}
