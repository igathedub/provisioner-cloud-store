import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { RetransmitUpdateComponent } from 'app/entities/retransmit/retransmit-update.component';
import { RetransmitService } from 'app/entities/retransmit/retransmit.service';
import { Retransmit } from 'app/shared/model/retransmit.model';

describe('Component Tests', () => {
  describe('Retransmit Management Update Component', () => {
    let comp: RetransmitUpdateComponent;
    let fixture: ComponentFixture<RetransmitUpdateComponent>;
    let service: RetransmitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [RetransmitUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RetransmitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RetransmitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RetransmitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Retransmit(123);
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
        const entity = new Retransmit();
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
