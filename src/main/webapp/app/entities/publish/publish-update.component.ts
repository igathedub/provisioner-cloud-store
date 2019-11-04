import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPublish, Publish } from 'app/shared/model/publish.model';
import { PublishService } from './publish.service';
import { IRetransmit } from 'app/shared/model/retransmit.model';
import { RetransmitService } from 'app/entities/retransmit/retransmit.service';
import { IModel } from 'app/shared/model/model.model';
import { ModelService } from 'app/entities/model/model.service';

@Component({
  selector: 'jhi-publish-update',
  templateUrl: './publish-update.component.html'
})
export class PublishUpdateComponent implements OnInit {
  isSaving: boolean;

  retransmits: IRetransmit[];

  models: IModel[];

  editForm = this.fb.group({
    id: [],
    index: [],
    credentials: [],
    ttl: [],
    period: [],
    address: [],
    retransmit: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected publishService: PublishService,
    protected retransmitService: RetransmitService,
    protected modelService: ModelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ publish }) => {
      this.updateForm(publish);
    });
    this.retransmitService
      .query({ filter: 'publish-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IRetransmit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRetransmit[]>) => response.body)
      )
      .subscribe(
        (res: IRetransmit[]) => {
          if (!this.editForm.get('retransmit').value || !this.editForm.get('retransmit').value.id) {
            this.retransmits = res;
          } else {
            this.retransmitService
              .find(this.editForm.get('retransmit').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IRetransmit>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IRetransmit>) => subResponse.body)
              )
              .subscribe(
                (subRes: IRetransmit) => (this.retransmits = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.modelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModel[]>) => response.body)
      )
      .subscribe((res: IModel[]) => (this.models = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(publish: IPublish) {
    this.editForm.patchValue({
      id: publish.id,
      index: publish.index,
      credentials: publish.credentials,
      ttl: publish.ttl,
      period: publish.period,
      address: publish.address,
      retransmit: publish.retransmit
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const publish = this.createFromForm();
    if (publish.id !== undefined) {
      this.subscribeToSaveResponse(this.publishService.update(publish));
    } else {
      this.subscribeToSaveResponse(this.publishService.create(publish));
    }
  }

  private createFromForm(): IPublish {
    return {
      ...new Publish(),
      id: this.editForm.get(['id']).value,
      index: this.editForm.get(['index']).value,
      credentials: this.editForm.get(['credentials']).value,
      ttl: this.editForm.get(['ttl']).value,
      period: this.editForm.get(['period']).value,
      address: this.editForm.get(['address']).value,
      retransmit: this.editForm.get(['retransmit']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPublish>>) {
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

  trackRetransmitById(index: number, item: IRetransmit) {
    return item.id;
  }

  trackModelById(index: number, item: IModel) {
    return item.id;
  }
}
