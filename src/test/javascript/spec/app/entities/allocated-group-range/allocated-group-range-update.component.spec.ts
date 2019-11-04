import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedGroupRangeUpdateComponent } from 'app/entities/allocated-group-range/allocated-group-range-update.component';
import { AllocatedGroupRangeService } from 'app/entities/allocated-group-range/allocated-group-range.service';
import { AllocatedGroupRange } from 'app/shared/model/allocated-group-range.model';

describe('Component Tests', () => {
  describe('AllocatedGroupRange Management Update Component', () => {
    let comp: AllocatedGroupRangeUpdateComponent;
    let fixture: ComponentFixture<AllocatedGroupRangeUpdateComponent>;
    let service: AllocatedGroupRangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedGroupRangeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllocatedGroupRangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllocatedGroupRangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedGroupRangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllocatedGroupRange(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllocatedGroupRange();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
