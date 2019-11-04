import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { KeyIndexUpdateComponent } from 'app/entities/key-index/key-index-update.component';
import { KeyIndexService } from 'app/entities/key-index/key-index.service';
import { KeyIndex } from 'app/shared/model/key-index.model';

describe('Component Tests', () => {
  describe('KeyIndex Management Update Component', () => {
    let comp: KeyIndexUpdateComponent;
    let fixture: ComponentFixture<KeyIndexUpdateComponent>;
    let service: KeyIndexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [KeyIndexUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(KeyIndexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KeyIndexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KeyIndexService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KeyIndex(123);
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
        const entity = new KeyIndex();
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
