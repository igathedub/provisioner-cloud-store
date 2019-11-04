import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AppKeyUpdateComponent } from 'app/entities/app-key/app-key-update.component';
import { AppKeyService } from 'app/entities/app-key/app-key.service';
import { AppKey } from 'app/shared/model/app-key.model';

describe('Component Tests', () => {
  describe('AppKey Management Update Component', () => {
    let comp: AppKeyUpdateComponent;
    let fixture: ComponentFixture<AppKeyUpdateComponent>;
    let service: AppKeyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AppKeyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AppKeyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AppKeyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AppKeyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AppKey(123);
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
        const entity = new AppKey();
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
