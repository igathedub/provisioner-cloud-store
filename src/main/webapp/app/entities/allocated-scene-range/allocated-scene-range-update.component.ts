import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAllocatedSceneRange, AllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';
import { AllocatedSceneRangeService } from './allocated-scene-range.service';
import { IProvisioner } from 'app/shared/model/provisioner.model';
import { ProvisionerService } from 'app/entities/provisioner/provisioner.service';

@Component({
  selector: 'jhi-allocated-scene-range-update',
  templateUrl: './allocated-scene-range-update.component.html'
})
export class AllocatedSceneRangeUpdateComponent implements OnInit {
  isSaving: boolean;

  provisioners: IProvisioner[];

  editForm = this.fb.group({
    id: [],
    lowAddress: [],
    highAddress: [],
    provisioner: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected allocatedSceneRangeService: AllocatedSceneRangeService,
    protected provisionerService: ProvisionerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allocatedSceneRange }) => {
      this.updateForm(allocatedSceneRange);
    });
    this.provisionerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProvisioner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvisioner[]>) => response.body)
      )
      .subscribe((res: IProvisioner[]) => (this.provisioners = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allocatedSceneRange: IAllocatedSceneRange) {
    this.editForm.patchValue({
      id: allocatedSceneRange.id,
      lowAddress: allocatedSceneRange.lowAddress,
      highAddress: allocatedSceneRange.highAddress,
      provisioner: allocatedSceneRange.provisioner
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allocatedSceneRange = this.createFromForm();
    if (allocatedSceneRange.id !== undefined) {
      this.subscribeToSaveResponse(this.allocatedSceneRangeService.update(allocatedSceneRange));
    } else {
      this.subscribeToSaveResponse(this.allocatedSceneRangeService.create(allocatedSceneRange));
    }
  }

  private createFromForm(): IAllocatedSceneRange {
    return {
      ...new AllocatedSceneRange(),
      id: this.editForm.get(['id']).value,
      lowAddress: this.editForm.get(['lowAddress']).value,
      highAddress: this.editForm.get(['highAddress']).value,
      provisioner: this.editForm.get(['provisioner']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllocatedSceneRange>>) {
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
