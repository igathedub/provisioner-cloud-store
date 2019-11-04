import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAllocatedUnicastRange, AllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';
import { AllocatedUnicastRangeService } from './allocated-unicast-range.service';
import { IProvisioner } from 'app/shared/model/provisioner.model';
import { ProvisionerService } from 'app/entities/provisioner/provisioner.service';

@Component({
  selector: 'jhi-allocated-unicast-range-update',
  templateUrl: './allocated-unicast-range-update.component.html'
})
export class AllocatedUnicastRangeUpdateComponent implements OnInit {
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
    protected allocatedUnicastRangeService: AllocatedUnicastRangeService,
    protected provisionerService: ProvisionerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ allocatedUnicastRange }) => {
      this.updateForm(allocatedUnicastRange);
    });
    this.provisionerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProvisioner[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProvisioner[]>) => response.body)
      )
      .subscribe((res: IProvisioner[]) => (this.provisioners = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(allocatedUnicastRange: IAllocatedUnicastRange) {
    this.editForm.patchValue({
      id: allocatedUnicastRange.id,
      lowAddress: allocatedUnicastRange.lowAddress,
      highAddress: allocatedUnicastRange.highAddress,
      provisioner: allocatedUnicastRange.provisioner
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const allocatedUnicastRange = this.createFromForm();
    if (allocatedUnicastRange.id !== undefined) {
      this.subscribeToSaveResponse(this.allocatedUnicastRangeService.update(allocatedUnicastRange));
    } else {
      this.subscribeToSaveResponse(this.allocatedUnicastRangeService.create(allocatedUnicastRange));
    }
  }

  private createFromForm(): IAllocatedUnicastRange {
    return {
      ...new AllocatedUnicastRange(),
      id: this.editForm.get(['id']).value,
      lowAddress: this.editForm.get(['lowAddress']).value,
      highAddress: this.editForm.get(['highAddress']).value,
      provisioner: this.editForm.get(['provisioner']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAllocatedUnicastRange>>) {
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
