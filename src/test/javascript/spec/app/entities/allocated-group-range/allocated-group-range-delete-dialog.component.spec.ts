import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { AllocatedGroupRangeDeleteDialogComponent } from 'app/entities/allocated-group-range/allocated-group-range-delete-dialog.component';
import { AllocatedGroupRangeService } from 'app/entities/allocated-group-range/allocated-group-range.service';

describe('Component Tests', () => {
  describe('AllocatedGroupRange Management Delete Component', () => {
    let comp: AllocatedGroupRangeDeleteDialogComponent;
    let fixture: ComponentFixture<AllocatedGroupRangeDeleteDialogComponent>;
    let service: AllocatedGroupRangeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [AllocatedGroupRangeDeleteDialogComponent]
      })
        .overrideTemplate(AllocatedGroupRangeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AllocatedGroupRangeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AllocatedGroupRangeService);
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
