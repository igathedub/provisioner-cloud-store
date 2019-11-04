import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedSceneRangeUpdateComponent } from 'app/entities/allocated-scene-range/allocated-scene-range-update.component';
import { AllocatedSceneRangeService } from 'app/entities/allocated-scene-range/allocated-scene-range.service';
import { AllocatedSceneRange } from 'app/shared/model/allocated-scene-range.model';

describe('Component Tests', () => {
  describe('AllocatedSceneRange Management Update Component', () => {
    let comp: AllocatedSceneRangeUpdateComponent;
    let fixture: ComponentFixture<AllocatedSceneRangeUpdateComponent>;
    let service: AllocatedSceneRangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedSceneRangeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AllocatedSceneRangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AllocatedSceneRangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedSceneRangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AllocatedSceneRange(123);
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
        const entity = new AllocatedSceneRange();
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