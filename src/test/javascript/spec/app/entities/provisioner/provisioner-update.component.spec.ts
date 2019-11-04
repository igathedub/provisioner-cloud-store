import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { ProvisionerUpdateComponent } from 'app/entities/provisioner/provisioner-update.component';
import { ProvisionerService } from 'app/entities/provisioner/provisioner.service';
import { Provisioner } from 'app/shared/model/provisioner.model';

describe('Component Tests', () => {
  describe('Provisioner Management Update Component', () => {
    let comp: ProvisionerUpdateComponent;
    let fixture: ComponentFixture<ProvisionerUpdateComponent>;
    let service: ProvisionerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [ProvisionerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProvisionerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProvisionerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProvisionerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Provisioner(123);
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
        const entity = new Provisioner();
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
