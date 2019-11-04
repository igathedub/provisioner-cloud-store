import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedUnicastRangeDeleteDialogComponent } from 'app/entities/allocated-unicast-range/allocated-unicast-range-delete-dialog.component';
import { AllocatedUnicastRangeService } from 'app/entities/allocated-unicast-range/allocated-unicast-range.service';

describe('Component Tests', () => {
  describe('AllocatedUnicastRange Management Delete Component', () => {
    let comp: AllocatedUnicastRangeDeleteDialogComponent;
    let fixture: ComponentFixture<AllocatedUnicastRangeDeleteDialogComponent>;
    let service: AllocatedUnicastRangeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedUnicastRangeDeleteDialogComponent]
      })
        .overrideTemplate(AllocatedUnicastRangeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedUnicastRangeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedUnicastRangeService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
