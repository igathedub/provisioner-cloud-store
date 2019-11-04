import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { ElementUpdateComponent } from 'app/entities/element/element-update.component';
import { ElementService } from 'app/entities/element/element.service';
import { Element } from 'app/shared/model/element.model';

describe('Component Tests', () => {
  describe('Element Management Update Component', () => {
    let comp: ElementUpdateComponent;
    let fixture: ComponentFixture<ElementUpdateComponent>;
    let service: ElementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [ElementUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ElementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Element(123);
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
        const entity = new Element();
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
