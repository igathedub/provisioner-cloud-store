import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NetKeyUpdateComponent } from 'app/entities/net-key/net-key-update.component';
import { NetKeyService } from 'app/entities/net-key/net-key.service';
import { NetKey } from 'app/shared/model/net-key.model';

describe('Component Tests', () => {
  describe('NetKey Management Update Component', () => {
    let comp: NetKeyUpdateComponent;
    let fixture: ComponentFixture<NetKeyUpdateComponent>;
    let service: NetKeyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NetKeyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NetKeyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NetKeyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NetKeyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NetKey(123);
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
        const entity = new NetKey();
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
