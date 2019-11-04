import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedUnicastRangeUpdateComponent } from 'app/entities/allocated-unicast-range/allocated-unicast-range-update.component';
import { AllocatedUnicastRangeService } from 'app/entities/allocated-unicast-range/allocated-unicast-range.service';
import { AllocatedUnicastRange } from 'app/shared/model/allocated-unicast-range.model';

describe('Component Tests', () => {
  describe('AllocatedUnicastRange Management Update Component', () => {
    let comp: AllocatedUnicastRangeUpdateComponent;
    let fixture: ComponentFixture<AllocatedUnicastRangeUpdateComponent>;
    let service: AllocatedUnicastRangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedUnicastRangeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllocatedUnicastRangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllocatedUnicastRangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedUnicastRangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllocatedUnicastRange(123);
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
        const entity = new AllocatedUnicastRange();
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
