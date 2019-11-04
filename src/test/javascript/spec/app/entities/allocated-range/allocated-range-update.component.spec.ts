import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedRangeUpdateComponent } from 'app/entities/allocated-range/allocated-range-update.component';
import { AllocatedRangeService } from 'app/entities/allocated-range/allocated-range.service';
import { AllocatedRange } from 'app/shared/model/allocated-range.model';

describe('Component Tests', () => {
  describe('AllocatedRange Management Update Component', () => {
    let comp: AllocatedRangeUpdateComponent;
    let fixture: ComponentFixture<AllocatedRangeUpdateComponent>;
    let service: AllocatedRangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedRangeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllocatedRangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllocatedRangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedRangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllocatedRange(123);
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
        const entity = new AllocatedRange();
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
