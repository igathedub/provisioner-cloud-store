import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAllocatedRange, AllocatedRange } from 'app/shared/model/allocated-range.model';
import { AllocatedRangeService } from './allocated-range.service';
import { IProvisioner } from 'app/shared/model/provisioner.model';
import { ProvisionerService } from 'app/entities/provisioner/provisioner.service';

@Component({
  selector: 'jhi-allocated-range-update',
  templateUrl: './allocated-range-update.component.html'
})
export class AllocatedRangeUpdateComponent implements OnInit {
  isSaving: boolean;

  provisioners: IProvisioner[];

  editForm = this.fb.group({
    id: [],
    lowAddress: [],
    highAddress: [],
    provisioner: [],
    provisioner: [],
    provisioner: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected allocatedRangeService: AllocatedRangeService,
    protected provisionerService: ProvisionerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allocatedRange }) => {
      this.updateForm(allocatedRange);
    });
    this.provisionerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProvisioner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvisioner[]>) => response.body)
      )
      .subscribe((res: IProvisioner[]) => (this.provisioners = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allocatedRange: IAllocatedRange) {
    this.editForm.patchValue({
      id: allocatedRange.id,
      lowAddress: allocatedRange.lowAddress,
      highAddress: allocatedRange.highAddress,
      provisioner: allocatedRange.provisioner,
      provisioner: allocatedRange.provisioner,
      provisioner: allocatedRange.provisioner
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allocatedRange = this.createFromForm();
    if (allocatedRange.id !== undefined) {
      this.subscribeToSaveResponse(this.allocatedRangeService.update(allocatedRange));
    } else {
      this.subscribeToSaveResponse(this.allocatedRangeService.create(allocatedRange));
    }
  }

  private createFromForm(): IAllocatedRange {
    return {
      ...new AllocatedRange(),
      id: this.editForm.get(['id']).value,
      lowAddress: this.editForm.get(['lowAddress']).value,
      highAddress: this.editForm.get(['highAddress']).value,
      provisioner: this.editForm.get(['provisioner']).value,
      provisioner: this.editForm.get(['provisioner']).value,
      provisioner: this.editForm.get(['provisioner']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllocatedRange>>) {
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

  trackProvisionerById(index: number, item: IProvisioner) {
    return item.id;
  }
}
