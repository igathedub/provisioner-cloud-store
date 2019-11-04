import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IModel, Model } from 'app/shared/model/model.model';
import { ModelService } from './model.service';
import { IPublish } from 'app/shared/model/publish.model';
import { PublishService } from 'app/entities/publish/publish.service';
import { IElement } from 'app/shared/model/element.model';
import { ElementService } from 'app/entities/element/element.service';

@Component({
  selector: 'jhi-model-update',
  templateUrl: './model-update.component.html'
})
export class ModelUpdateComponent implements OnInit {
  isSaving: boolean;

  publishes: IPublish[];

  elements: IElement[];

  editForm = this.fb.group({
    id: [],
    modelId: [],
    subscribe: [],
    bind: [],
    publish: [],
    element: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected modelService: ModelService,
    protected publishService: PublishService,
    protected elementService: ElementService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ model }) => {
      this.updateForm(model);
    });
    this.publishService
      .query({ filter: 'model-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPublish[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPublish[]>) => response.body)
      )
      .subscribe(
        (res: IPublish[]) => {
          if (!this.editForm.get('publish').value || !this.editForm.get('publish').value.id) {
            this.publishes = res;
          } else {
            this.publishService
              .find(this.editForm.get('publish').value.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPublish>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPublish>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPublish) => (this.publishes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.elementService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IElement[]>) => mayBeOk.ok),
        map((response: HttpResponse<IElement[]>) => response.body)
      )
      .subscribe((res: IElement[]) => (this.elements = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(model: IModel) {
    this.editForm.patchValue({
      id: model.id,
      modelId: model.modelId,
      subscribe: model.subscribe,
      bind: model.bind,
      publish: model.publish,
      element: model.element
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const model = this.createFromForm();
    if (model.id !== undefined) {
      this.subscribeToSaveResponse(this.modelService.update(model));
    } else {
      this.subscribeToSaveResponse(this.modelService.create(model));
    }
  }

  private createFromForm(): IModel {
    return {
      ...new Model(),
      id: this.editForm.get(['id']).value,
      modelId: this.editForm.get(['modelId']).value,
      subscribe: this.editForm.get(['subscribe']).value,
      bind: this.editForm.get(['bind']).value,
      publish: this.editForm.get(['publish']).value,
      element: this.editForm.get(['element']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModel>>) {
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

  trackPublishById(index: number, item: IPublish) {
    return item.id;
  }

  trackElementById(index: number, item: IElement) {
    return item.id;
  }
}
