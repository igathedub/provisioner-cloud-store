import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRetransmit, Retransmit } from 'app/shared/model/retransmit.model';
import { RetransmitService } from './retransmit.service';
import { IPublish } from 'app/shared/model/publish.model';
import { PublishService } from 'app/entities/publish/publish.service';

@Component({
  selector: 'jhi-retransmit-update',
  templateUrl: './retransmit-update.component.html'
})
export class RetransmitUpdateComponent implements OnInit {
  isSaving: boolean;

  publishes: IPublish[];

  editForm = this.fb.group({
    id: [],
    count: [],
    interval: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected retransmitService: RetransmitService,
    protected publishService: PublishService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ retransmit }) => {
      this.updateForm(retransmit);
    });
    this.publishService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPublish[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPublish[]>) => response.body)
      )
      .subscribe((res: IPublish[]) => (this.publishes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(retransmit: IRetransmit) {
    this.editForm.patchValue({
      id: retransmit.id,
      count: retransmit.count,
      interval: retransmit.interval
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const retransmit = this.createFromForm();
    if (retransmit.id !== undefined) {
      this.subscribeToSaveResponse(this.retransmitService.update(retransmit));
    } else {
      this.subscribeToSaveResponse(this.retransmitService.create(retransmit));
    }
  }

  private createFromForm(): IRetransmit {
    return {
      ...new Retransmit(),
      id: this.editForm.get(['id']).value,
      count: this.editForm.get(['count']).value,
      interval: this.editForm.get(['interval']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRetransmit>>) {
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
}
