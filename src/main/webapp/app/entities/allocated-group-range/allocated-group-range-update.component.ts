import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAllocatedGroupRange, AllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';
import { AllocatedGroupRangeService } from './allocated-group-range.service';
import { IProvisioner } from 'app/shared/model/provisioner.model';
import { ProvisionerService } from 'app/entities/provisioner/provisioner.service';

@Component({
  selector: 'jhi-allocated-group-range-update',
  templateUrl: './allocated-group-range-update.component.html'
})
export class AllocatedGroupRangeUpdateComponent implements OnInit {
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
    protected allocatedGroupRangeService: AllocatedGroupRangeService,
    protected provisionerService: ProvisionerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allocatedGroupRange }) => {
      this.updateForm(allocatedGroupRange);
    });
    this.provisionerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProvisioner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvisioner[]>) => response.body)
      )
      .subscribe((res: IProvisioner[]) => (this.provisioners = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allocatedGroupRange: IAllocatedGroupRange) {
    this.editForm.patchValue({
      id: allocatedGroupRange.id,
      lowAddress: allocatedGroupRange.lowAddress,
      highAddress: allocatedGroupRange.highAddress,
      provisioner: allocatedGroupRange.provisioner
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allocatedGroupRange = this.createFromForm();
    if (allocatedGroupRange.id !== undefined) {
      this.subscribeToSaveResponse(this.allocatedGroupRangeService.update(allocatedGroupRange));
    } else {
      this.subscribeToSaveResponse(this.allocatedGroupRangeService.create(allocatedGroupRange));
    }
  }

  private createFromForm(): IAllocatedGroupRange {
    return {
      ...new AllocatedGroupRange(),
      id: this.editForm.get(['id']).value,
      lowAddress: this.editForm.get(['lowAddress']).value,
      highAddress: this.editForm.get(['highAddress']).value,
      provisioner: this.editForm.get(['provisioner']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllocatedGroupRange>>) {
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
