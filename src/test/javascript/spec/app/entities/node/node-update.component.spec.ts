import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NodeUpdateComponent } from 'app/entities/node/node-update.component';
import { NodeService } from 'app/entities/node/node.service';
import { Node } from 'app/shared/model/node.model';

describe('Component Tests', () => {
  describe('Node Management Update Component', () => {
    let comp: NodeUpdateComponent;
    let fixture: ComponentFixture<NodeUpdateComponent>;
    let service: NodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NodeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Node(123);
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
        const entity = new Node();
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
